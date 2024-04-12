package com.example.vsuex1.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.vsuex1.databinding.FragmentFirstBinding

    private const val SURF_SECRET_KEY = "SURF_SECRET_KEY"
/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var surfSecretKey: String = ""


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getSecretKey()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val secretKeyInstanceState = savedInstanceState?.getString(SURF_SECRET_KEY)
        if (secretKeyInstanceState!=null) {
            surfSecretKey = secretKeyInstanceState
            Log.i(SURF_SECRET_KEY, surfSecretKey)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(
            SURF_SECRET_KEY,
            surfSecretKey
        )
    }

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getSecretKey(){
        val uri = Uri.parse("content://dev.surf.android.provider/text");
        val cursor = context?.contentResolver?.query(uri,null,null,null)
        if (cursor != null && cursor.moveToFirst()) {
            surfSecretKey = cursor.getString(cursor.getColumnIndex("text"))
            Log.i("secretKey", surfSecretKey)
            Toast.makeText(context,surfSecretKey, Toast.LENGTH_LONG).show()
            cursor.close()
        }
    }

}