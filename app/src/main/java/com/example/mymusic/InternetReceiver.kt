import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

class InternetReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return

            val isInternetAvailable =
                actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            if (isInternetAvailable) {
                // Internet connection is available
                Toast.makeText(context, "Internet connection available", Toast.LENGTH_SHORT).show()
            } else {
                // No internet connection
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            val isInternetAvailable = networkInfo != null && networkInfo.isConnected
            if (isInternetAvailable) {
                Toast.makeText(context, "Internet connection available", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
