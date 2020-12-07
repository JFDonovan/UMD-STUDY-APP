package com.example.umd_study_app

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.abs

class FlashcardActivity : AppCompatActivity() {

    private var mSetRightOut : AnimatorSet? = null
    private var mSetLeftIn : AnimatorSet? = null
    private var mIsBackVisible = false
    private var mCardFrontLayout : View? = null
    private var mCardBackLayout : View? = null

    private var mCardFrontText : TextView? = null
    private var mCardBackText : TextView? = null

    private var mFlashcards: HashMap<String, ArrayList<String>>? = null
    private var mFlashcardKeyList: ArrayList<String> = arrayListOf()
    private var mCurrentFlashcard: Int = 0

    private lateinit var mClassId : String

    // Swipe variables
    private var touchX1 : Float = -1F
    private val MIN_DISTANCE = 150

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.flashcard_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(AddFlashcardActivity.TAG, "${item.title}")
        if (item.title == "Add Flashcard") {
            val intent = Intent(this, AddFlashcardActivity::class.java).apply {
                putExtra("classId", mClassId)
            }
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard)

        mClassId = intent.getStringExtra("classId").toString()

        mCardFrontLayout = findViewById(R.id.card_front)
        mCardBackLayout = findViewById(R.id.card_back)
        mCardFrontText = findViewById(R.id.flashcardFrontTextView)
        mCardBackText = findViewById(R.id.flashcardBackTextView)
        mSetRightOut = AnimatorInflater.loadAnimator(this, R.animator.flip_out_animation) as AnimatorSet
        mSetLeftIn = AnimatorInflater.loadAnimator(this, R.animator.flip_in_animation) as AnimatorSet
        changeCameraDistance()

        mFlashcards = intent.extras?.get("flashcards") as HashMap<String, ArrayList<String>>
        /*for ((key, _) in mFlashcards!!) {
            mFlashcardKeyList.add(key)
        }*/
        setFlashcardText()

        FirebaseDatabase.getInstance().getReference("Classes").child(mClassId!!).child("flashcards").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i(DashboardActivity.TAG, "CLASS DATABASE OBJECT CHANGED: $mClassId")
                var cardsListTemp = hashMapOf<String, ArrayList<String>>()
                for (ds in dataSnapshot.children) {
                    cardsListTemp[ds.key!!] = arrayListOf()
                    for(li in ds.children) {
                        cardsListTemp[ds.key!!]?.add(li.getValue(String::class.java)!!)
                    }
                }
                mFlashcards = cardsListTemp
                setFlashcardText()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e(DashboardActivity.TAG, "FAILED TO READ CLASS DATABASE")
            }
        })
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            touchX1 = event.x
            return super.dispatchTouchEvent(event)
        }
        else if (event!!.action == MotionEvent.ACTION_UP) {
            var distance = event.x - touchX1
            if (distance >= MIN_DISTANCE) {
                Log.i(TAG, "SWIPE RIGHT")
                previousCard()
            }
            else if (abs(distance) >= MIN_DISTANCE) {
                Log.i(TAG, "SWIPE LEFT")
                nextCard()
            }
            else {
                return super.dispatchTouchEvent(event)
            }
        }
        else {
            return super.dispatchTouchEvent(event)
        }
        return false
    }

    private fun setFlashcardText() {
        mFlashcardKeyList = arrayListOf()
        for ((key, _) in mFlashcards!!) {
            mFlashcardKeyList.add(key)
        }
        if (mFlashcardKeyList.size > 0) {
            mCardFrontText!!.text = mFlashcards!![mFlashcardKeyList!![mCurrentFlashcard]]!![0]
            mCardBackText!!.text = mFlashcards!![mFlashcardKeyList!![mCurrentFlashcard]]!![1]
        }
        else {
            val toast = Toast.makeText(this, "No Flashcards in this class.  Add some using plus icon at top.", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun changeCameraDistance() {
        var distance = 8000
        var scale = resources.displayMetrics.density * distance
        mCardBackLayout!!.cameraDistance = scale
        mCardFrontLayout!!.cameraDistance = scale
    }

    fun nextCard() {
        if (mCurrentFlashcard < mFlashcardKeyList.size-1) {
            mCurrentFlashcard += 1
            /*if (mIsBackVisible) {
                flipCard(null)
            }*/
            setFlashcardText()
        }
        else {
            val toast = Toast.makeText(this, "No Next Card", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun previousCard() {
        if (mCurrentFlashcard > 0) {
            mCurrentFlashcard -= 1
            /*if (mIsBackVisible) {
                flipCard(null)
            }*/
            setFlashcardText()
        }
        else {
            val toast = Toast.makeText(this, "No Previous Card", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun flipCard(view: View?) {
        mIsBackVisible = if (!mIsBackVisible) {
            mSetRightOut!!.setTarget(mCardFrontLayout)
            mSetLeftIn!!.setTarget(mCardBackLayout)
            mSetRightOut!!.start()
            mSetLeftIn!!.start()
            true
        } else {
            mSetRightOut!!.setTarget(mCardBackLayout);
            mSetLeftIn!!.setTarget(mCardFrontLayout);
            mSetRightOut!!.start();
            mSetLeftIn!!.start();
            false
        }
    }

    companion object {
        const val TAG = "UMD-STUDY-APP"
    }
}