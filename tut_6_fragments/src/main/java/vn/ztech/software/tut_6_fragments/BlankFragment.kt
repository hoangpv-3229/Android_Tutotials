package vn.ztech.software.tut_6_fragments

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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView =
            inflater.inflate(R.layout.fragment_blank, container, false);
        val radioGroup = rootView.findViewById<RadioGroup>(R.id.radio_group)
        val ratingBar = rootView.findViewById<RatingBar>(R.id.ratingBar)

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radioButton: View = radioGroup.findViewById<View>(checkedId)
            val index: Int = radioGroup.indexOfChild(radioButton)
            val textView: TextView = rootView.findViewById<TextView>(R.id.fragment_header)
            when (index) {
                YES -> textView.setText(R.string.yes_message)
                NO -> textView.setText(R.string.no_message)
                else -> {}
            }
        })

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            Toast.makeText(requireContext(), "Rating: $rating", Toast.LENGTH_LONG).show()
        }
        return rootView
    }

    companion object {
        fun newInstance(param1: String, param2: String) {
        }
    }
}