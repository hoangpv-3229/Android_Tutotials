package vn.ztech.software.tut_7_fragment_lifecycle_and_communication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


class BlankFragment : Fragment() {
    private val YES = 0
    private val NO = 1
    private var prevChoice = -1
    private var prevRating = -1.0f
    var callBackListener: FragmentCallback? = null

    interface FragmentCallback{
        fun fragmentClose(prevChoice: Int, prevRating: Float)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            if(it.containsKey(PREV_CHOICE_FLAG)){
                prevChoice = it.getInt(PREV_CHOICE_FLAG)
            }

            if(it.containsKey(PREV_RATING_FLAG)){
                prevRating = it.getFloat(PREV_RATING_FLAG)
            }
        }

        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView =
            inflater.inflate(R.layout.fragment_blank, container, false);
        val radioGroup = rootView.findViewById<RadioGroup>(R.id.radio_group)
        val ratingBar = rootView.findViewById<RatingBar>(R.id.ratingBar)

        setPrevSelectedRadioButton(radioGroup, prevChoice)
        setPrevRating(ratingBar, prevRating)
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radioButton: View = radioGroup.findViewById<View>(checkedId)
            val index: Int = radioGroup.indexOfChild(radioButton)
            val textView: TextView = rootView.findViewById<TextView>(R.id.fragment_header)
            when (index) {
                YES -> {
                    textView.setText(R.string.yes_message)
                    prevChoice = YES
                }
                NO -> {
                    textView.setText(R.string.no_message)
                    prevChoice = NO
                }
                else -> {}
            }
        })

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            Toast.makeText(requireContext(), "Rating: $rating", Toast.LENGTH_LONG).show()
            prevRating = rating
        }
        return rootView
    }

    private fun setPrevRating(ratingBar: RatingBar?, prevRating: Float) {
        ratingBar?.let {
            if (prevRating>=0){
                it.rating = prevRating
            }
        }
    }

    private fun setPrevSelectedRadioButton(radioGroup: RadioGroup?, prevChoice: Int) {
        radioGroup?.let {
            if (prevChoice>=0){
                when(prevChoice){
                    YES -> radioGroup.check(R.id.radio_button_yes)
                    NO -> radioGroup.check(R.id.radio_button_no)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentCallback){
            callBackListener = context
        }else{
            throw ClassCastException("${context.toString()}")
        }
    }

    override fun onStop() {
        super.onStop()
        callBackListener?.fragmentClose(prevChoice, prevRating)
    }

    companion object {
        fun newInstance(prevChoice: Int, prevRating: Float): BlankFragment {
            val fragment = BlankFragment()
            val bundle = Bundle()
            bundle.putInt(PREV_CHOICE_FLAG, prevChoice)
            bundle.putFloat(PREV_RATING_FLAG, prevRating)

            fragment.arguments = bundle
            return fragment
        }

        const val PREV_CHOICE_FLAG = "PREV_CHOICE"
        const val PREV_RATING_FLAG = "PREV_RATING"
    }
}