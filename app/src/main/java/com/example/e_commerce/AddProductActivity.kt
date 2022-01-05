package com.example.e_commerce

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.e_commerce.model.Product
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AddProductActivity : AppCompatActivity() {
    private lateinit var mDatabase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        mDatabase = Firebase.database.reference

        val database = Firebase.database
        mDatabase = database.getReference("Products")

        mDatabase.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val Json = JSONObject(dataSnapshot.value.toString())
                val log = mutableListOf<String>()
                val result_array: JSONArray = Json.getJSONArray("Product")
                for (i in 0 until result_array.length()) {
                    log.add(result_array.getString(i))
                    Log.d("arom", result_array.getString(i))
                }
                Log.d("Name", result_array.getString(0))
                Log.d("Product", log.toString())
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                return
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                return
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                return
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("online", "postComments:onCancelled", databaseError.toException())
                Toast.makeText(
                    this@AddProductActivity,
                    "Failed to load comments.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        var button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val products = mutableListOf<String>()
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            try {
                val productName = findViewById<EditText>(R.id.editTextProductName).text
                val productPrice = findViewById<EditText>(R.id.editTextPrice).text
                val productQuantity= findViewById<EditText>(R.id.editTextQuantity).text
                products.add(productName.toString())
                products.add(productPrice.toString())
                products.add(productQuantity.toString())
                Toast.makeText(this, "ProductInserted", Toast.LENGTH_SHORT).show()
                val Json_result = JSONObject("""{"Product":${JSONArray(products)}}""")
                mDatabase.child(timeStamp).setValue(Json_result.toString())
            }
            catch(Error: Exception) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

}