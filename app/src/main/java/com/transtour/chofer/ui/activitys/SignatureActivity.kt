package com.transtour.chofer.ui.activitys

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import com.kyanogen.signatureview.SignatureView
import com.transtour.chofer.R
import com.transtour.chofer.util.BaseActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import java.util.*

class SignatureActivity : BaseActivity() {

    private lateinit var bitmap: Bitmap
    private lateinit var clear: Button
    private lateinit var save: Button
    private lateinit var signatureView: SignatureView
    private var path: String = ""
    private var image_DIRECTORY: String = "/sing"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signature)

        signatureView = findViewById(R.id.signatureView)
        save = findViewById(R.id.signatureSave)
        clear = findViewById(R.id.signatureClear)


        clear.setOnClickListener { it ->
            signatureView.clearCanvas()
        }

        save.setOnClickListener { it ->
            bitmap=signatureView.signatureBitmap
            path=saveImage(resizeBitmap(bitmap,180,36))
            val intent:Intent = Intent()
            intent.apply {
                putExtra("path",path)
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    fun saveImage(bitmap: Bitmap?) :String {
        var result = ""

        try {
     // val bytes = ByteArrayOutputStream()
        var fileDirectory: File? = null

        fileDirectory = File(getExternalFilesDir(Environment.DIRECTORY_DCIM) , image_DIRECTORY)

        if (android.os.Build.VERSION.SDK_INT >= 29) {
            fileDirectory = File(getExternalFilesDir(Environment.DIRECTORY_DCIM) , image_DIRECTORY)

        } else {
             fileDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) , image_DIRECTORY)

        }

        if (!fileDirectory?.exists()!!){
            fileDirectory?.mkdirs()
            Log.d("file directory: ", fileDirectory.toString())
        }


            val file = File(fileDirectory, "${UUID.randomUUID()}.jPEG")
            if(file.canWrite()){
                file.createNewFile()
            }
            val stream: OutputStream = FileOutputStream(file)

            // Compress the bitmap
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, stream)

            // Flush the output stream
            stream.flush()

            // Close the output stream
            stream.close()
            Log.d("File Created", "File Created")
            result = file.absolutePath
        }catch (e:Exception) {
          //  Log.d("File Exception", "File not Created Exception")
          //  Toast.makeText(applicationContext,e.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            throw Exception();
        }
        return result
    }

    private fun resizeBitmap(bitmap:Bitmap, width:Int, height:Int):Bitmap{
        return Bitmap.createScaledBitmap(
            bitmap,
            width,
            height,
            true
        )
    }
}