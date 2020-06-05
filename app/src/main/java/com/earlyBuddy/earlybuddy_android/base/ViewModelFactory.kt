import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.newInstance()
    }
}