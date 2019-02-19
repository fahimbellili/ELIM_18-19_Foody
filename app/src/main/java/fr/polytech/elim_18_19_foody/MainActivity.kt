package fr.polytech.elim_18_19_foody

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.mindorks.paracamera.Camera
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var camera: Camera
    private val PERMISSION_REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init Firebase
        FirebaseApp.initializeApp(this)

        // Configure Camera
        camera = Camera.Builder()
            .resetToCorrectOrientation(true)
            .setTakePhotoRequestCode(Camera.REQUEST_TAKE_PHOTO)
            .setDirectory("pics")//3
            .setName("delicious_${System.currentTimeMillis()}")
            .setImageFormat(Camera.IMAGE_JPEG)
            .setCompression(75)
            .build(this)
    }

    fun takePicture(view: View) {
        // provide an implementation
        if (!hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
            !hasPermission(android.Manifest.permission.CAMERA)
        ) {
            // If do not have permissions then request it
            requestPermissions()
        } else {
            // else all permissions granted, go ahead and take a picture using camera
            try {
                camera.takePicture()
            } catch (e: Exception) {
                // Show a toast for exception
                Toast.makeText(
                    this.applicationContext, getString(R.string.error_taking_picture),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        // provide an implementation
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {

            mainLayout.snack(getString(R.string.permission_message), Snackbar.LENGTH_INDEFINITE) {
                action(getString(R.string.OK)) {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA
                        ), PERMISSION_REQUEST_CODE
                    )
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
                ), PERMISSION_REQUEST_CODE
            )
            return
        }
    }
}
