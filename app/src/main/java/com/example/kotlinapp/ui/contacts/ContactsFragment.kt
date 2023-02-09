package com.example.kotlinapp.ui.contacts

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentContactsBinding
import com.example.kotlinapp.utils.startThread

const val REQUEST_CODE = 42

const val HANDLER_KEY = "HANDLER_KEY "

class ContactsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = ContactsFragment()
    }

    private var _binding: FragmentContactsBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var viewModel: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addView(context: Context, textToShow: String) {
        binding.containerForContacts.addView(AppCompatTextView(context).apply {
            text=textToShow
            textSize = resources.getDimension(R.dimen.main_container_text_size)
        })
    }

    private fun checkPermission() {
        context?.let {

            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getContacts()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к контактам")
                        .setMessage("Для оттображнеи страницы с контактами, просим предоставить доступ")
                        .setPositiveButton("Предоставить доступ") { _, _ ->
                            requestPermission()
                        }
                        .setNegativeButton("Нет") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult (
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts()
                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Доступ к контактам")
                            .setMessage("Доступ к контактам не предоставлен. Страница не может быть отображена")
                            .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
            }
        }
    }

    private fun getContacts() {
        context?.let {
            val contentResolver : ContentResolver = it.contentResolver
            val handler = object : Handler() {
                @Override
                override fun handleMessage(msg: Message) {
                    val bundle = msg.data
                    val date = bundle.getString(HANDLER_KEY)
                    if (date != null) {
                        addView(it, date)
                    }
                }
            }
            startThread {
                val cursorWithContacts: Cursor? = contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC"
                )

                cursorWithContacts?.let { cursor ->
                    for (i in 0..cursor.count) {
                        val pos = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

                        if (cursor.moveToPosition(i)) {
                            val name = cursor.getString(pos)
                            val msg = handler.obtainMessage()
                            val bundle = Bundle()
                            bundle.putString(HANDLER_KEY, name);
                            msg.data = bundle;
                            handler.sendMessage(msg);
                        }
                    }
                }

                cursorWithContacts?.close()
            }
        }
    }
}