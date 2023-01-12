import android.content.Context
import android.content.SharedPreferences
import com.huawei.hms.maps.HuaweiMap

object SharedPreferencesHelper {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private const val PREFS_NAME = "project"

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun putString(key: String, value: String) {
        editor.putString(key, value).commit()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    private fun putValue(block: SharedPreferences.Editor.() -> Unit) {
        editor.apply {
            block()
        }.commit()
    }

    fun putInt(key: String, value: Int) {
        putValue {
            putInt(key, value)
        }
    }

    fun getInt(key: String, defaultValue: Int = -1): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun putFloat(key: String, value: Float) {
        putValue {
            putFloat(key, value)
        }
    }

    fun getFloat(key: String, defaultValue: Float = -1F): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }
}
