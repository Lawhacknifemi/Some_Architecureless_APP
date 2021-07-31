package com.example.blingytest

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blingytest.data.People
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import android.widget.PopupWindow
import androidx.transition.Slide
import androidx.transition.TransitionManager


private lateinit var firebasefireStore :FirebaseFirestore


class MainActivity : AppCompatActivity() {



//   lateinit var detalTVLayout :ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    var isVisible = false


    val getDialogFragment = CustomisedDialogFragment()

    val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    // Inflate a custom view using layout inflater
    val view = inflater.inflate(R.layout.profile_image_layout,null)

    // Initialize a new instance of popup window
    val popupWindow = PopupWindow(
        view, // Custom view to show in popup window
        LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
        LinearLayout.LayoutParams.WRAP_CONTENT // Window height
    )


        val progressBar = findViewById<ProgressBar>(R.id.progressBar)


        firebasefireStore = FirebaseFirestore.getInstance()

       val fireStoreList =  findViewById<RecyclerView>(R.id.fire_store_list_rv)

//        Query
        val query: Query = firebasefireStore.collection("People")
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(10)
            .setPageSize(3)
            .build()
//RecyclerOption
        val options = FirestorePagingOptions.Builder<People>()
            .setQuery(query,config,People::class.java)
            .setLifecycleOwner(this)
            .build()

        val adapter = object : FirestorePagingAdapter<People, PeopleViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
             val view = LayoutInflater.from(parent.context).inflate(R.layout.list_items,parent,false)
                return PeopleViewHolder(view)



            }

            override fun onBindViewHolder(holder: PeopleViewHolder, position: Int, model: People) {
                holder.name.text = model.Name
                holder.location.text = model.Location
                val detailsView = holder.detailTVLayout

                val profileImage = holder.profileImg

                profileImage.setOnClickListener {

                    // Initialize a new layout inflater instance

                    // Set an elevation for the popup window
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        popupWindow.elevation = 10.0F
                    }


                    // If API level 23 or higher then execute the code
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // Create a new slide animation for popup window enter transition
                        val slideIn = Slide()
                        slideIn.slideEdge = Gravity.TOP


                        // Slide animation for popup window exit transition
                        val slideOut = Slide()
                        slideOut.slideEdge = Gravity.RIGHT
                    }

                    TransitionManager.beginDelayedTransition(findViewById(R.id.fire_store_list_rv))
                    popupWindow.showAtLocation(
                        findViewById(R.id.fire_store_list_rv), // Location to display popup window
                        Gravity.CENTER, // Exact position of layout to display popup
                        0, // X offset
                        0 // Y offset
                    )

                }
                popupWindow.isOutsideTouchable = true
                    popupWindow.dismiss()


                holder.itemView.setOnClickListener {
                    if (!isVisible){
                        detailsView.visibility = View.VISIBLE
                        isVisible = true
                    }else{
                        detailsView.setOnClickListener {
                            detailsView.visibility = View.GONE
                            isVisible = false
                        }
                        detailsView.visibility = View.GONE
                        isVisible = false

                    }


                }
            }

            override fun onLoadingStateChanged(state: LoadingState) {
                super.onLoadingStateChanged(state)
                when(state){
                    LoadingState.LOADED->{
                        progressBar.visibility = View.GONE
                    }
                    LoadingState.LOADING_INITIAL ->{
                        progressBar.visibility = View.VISIBLE
                    }
                    LoadingState.LOADING_MORE ->{
                        progressBar.visibility = View.VISIBLE
                    }
                    LoadingState.FINISHED ->{
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }
        fireStoreList.setHasFixedSize(true)
        fireStoreList.layoutManager = LinearLayoutManager(this)

        fireStoreList.adapter = adapter





//        adapter.startListening()



    }



}
