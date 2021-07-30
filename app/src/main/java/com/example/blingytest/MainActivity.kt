package com.example.blingytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.blingytest.data.People
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

private lateinit var firebasefireStore :FirebaseFirestore


class MainActivity : AppCompatActivity() {



//   lateinit var detalTVLayout :ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    var isVisible = false


//        detalTVLayout = findViewById(R.id.detailsTvLayout)

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
