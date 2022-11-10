import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.NumberPicker
import android.widget.TimePicker

val style = AlertDialog.THEME_HOLO_DARK
class DurationPicker(
    context: Context?,
    private val callback: OnTimeSetListener?,
    private val initialMinutes: Int,
    seconds: Int
) :
    TimePickerDialog(context,style, callback, initialMinutes, seconds, true) {
    private var timePicker: TimePicker? = null
    override fun onClick(dialog: DialogInterface, which: Int) {
        if (callback != null && timePicker != null) {
            timePicker!!.clearFocus()
            callback.onTimeSet(timePicker, timePicker!!.currentHour, timePicker!!.currentMinute)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        try {
            val classForid = Class.forName("com.android.internal.R\$id")
            val timePickerField = classForid.getField("timePicker")
            timePicker = findViewById<View>(timePickerField.getInt(null)) as TimePicker
            val field = classForid.getField("hour")

            // modify the hours spinner to cover the maximum number of minutes we want to support
            val maxMinutes = 60
            val mHourSpinner = timePicker!!.findViewById<View>(field.getInt(null)) as NumberPicker
            mHourSpinner.minValue = 0
            mHourSpinner.maxValue = maxMinutes
            val displayedValues: MutableList<String> = ArrayList()
            for (i in 0..maxMinutes) {
                displayedValues.add(String.format("%d", i))
            }
            mHourSpinner.displayedValues = displayedValues.toTypedArray()
            mHourSpinner.value =
                initialMinutes // we can set this again now that we've modified the hours spinner
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    init {
        // we'll have to set this again after modifying the "hours" spinner
        this.setTitle("Set duration")
    }
}