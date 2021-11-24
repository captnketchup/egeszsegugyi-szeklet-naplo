package hu.verymucharealcompany.eszn.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isNotEmpty
import androidx.fragment.app.DialogFragment
import hu.verymucharealcompany.eszn.R
import hu.verymucharealcompany.eszn.data.DiaryItem
import hu.verymucharealcompany.eszn.databinding.DialogNewDiaryItemBinding

class NewDiaryItemDialogFragment : DialogFragment() {
    interface NewDiaryItemDialogListener {
        fun onDiaryItemCreated(newItem: DiaryItem)
    }

    private lateinit var listener: NewDiaryItemDialogListener

    private lateinit var binding: DialogNewDiaryItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewDiaryItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewDiaryItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewDiaryItemBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_diary_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onDiaryItemCreated(getDiaryItem())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun isValid() = binding.dpDate.isNotEmpty()

    private fun getDiaryItem() = DiaryItem(
        description = binding.etDescription.text.toString(),
        weight = getWeightDiff(),
        date = binding.dpDate.year.toString() + "." + (binding.dpDate.month+1).toString() + "." + binding.dpDate.dayOfMonth.toString()  //for some reason binding.dpDate.month returns a
    )

    private fun getWeightDiff(): Double {
        val weightBefore = binding.etWeightBef.text.toString().toDoubleOrNull()
        val weightAfter = binding.etWeightAft.text.toString().toDoubleOrNull()
        if (weightBefore != null && weightAfter != null) {
            return Math.round((weightBefore - weightAfter) * 100.0) / 100.0
        }
        return 0.0
    }

    companion object {
        const val TAG = "NewDiaryItemDialogFragment"
    }
}
