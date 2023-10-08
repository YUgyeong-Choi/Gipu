package com.example.gipu_android

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date

class ChatRoomActivity :AppCompatActivity() {
    private val fireDatabase = FirebaseDatabase.getInstance().reference
    private var chatRoomUid: String? = null
    private var destinationUid: String? = null
    private var uid: String? = null
    private var recyclerView: RecyclerView? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatroom_activity)

        val backBtn = findViewById<ImageView>(R.id.chatroom_back)
        backBtn.setOnClickListener {
            val intent = Intent(this, ChatListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
            finish()
        }

        val sendBtn = findViewById<Button>(R.id.chatroom_send)
        val editText = findViewById<TextView>(R.id.chatroom_input)

        //메세지를 보낸 시간
        val time = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("MM월dd일 hh:mm")
        val curTime = dateFormat.format(Date(time)).toString()

        // 대상 이름
        destinationUid = intent.getStringExtra("destinationUid")

        // 유저 이름
        ProfileActivity.UserDB.init(this)
        val userName = ProfileActivity.UserDB.getInstance().getString("user", "")
        uid = userName
        recyclerView = findViewById(R.id.chatroom_recyclerview)

        sendBtn.setOnClickListener {
            Log.d("클릭 시 dest", "$destinationUid")
            val chatModel = ChatModel()
            chatModel.users.put(uid.toString(), true)
            chatModel.users.put(destinationUid!!, true)

            val roomName = intent.getStringExtra("roomName")

            val comment = Comment(roomName, uid, editText.text.toString(), curTime)
            if (chatRoomUid == null) {
                sendBtn.isEnabled = false
                fireDatabase.child("chatrooms").push().setValue(chatModel).addOnSuccessListener {
                    //채팅방 생성
                    checkChatRoom(sendBtn)
                    //메세지 보내기
                    Handler().postDelayed({
                        Log.d("채팅방 이름", chatRoomUid.toString())
                        fireDatabase.child("chatrooms").child(chatRoomUid.toString())
                            .child("comments").push().setValue(comment)
                        editText.text = null
                    }, 1000L)
                    Log.d("chatUidNull dest", "$destinationUid")
                }
            } else {
                fireDatabase.child("chatrooms").child(chatRoomUid.toString()).child("comments")
                    .push().setValue(comment)
                editText.text = null
                Log.d("chatUidNotNull dest", "$destinationUid")
            }
        }
        checkChatRoom(sendBtn)
    }

    private fun checkChatRoom(sendBtn : View) {
        fireDatabase.child("chatrooms").orderByChild("users/$uid").equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (item in snapshot.children) {
                        println(item)
                        val chatModel = item.getValue(ChatModel::class.java)
                        if (chatModel?.users!!.containsKey(destinationUid)) {
                            chatRoomUid = item.key
                            sendBtn.isEnabled = true
                            recyclerView?.layoutManager = LinearLayoutManager(this@ChatRoomActivity)
                            recyclerView?.adapter = RecyclerViewAdapter()
                        }
                    }
                }
            })
    }
    inner class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.MessageViewHolder>() {

        private val comments = ArrayList<Comment>()
        init{
            fireDatabase.child("users").child(destinationUid.toString()).child("name").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    getMessageList()
                }
            })
        }

        fun getMessageList(){
            fireDatabase.child("chatrooms").child(chatRoomUid.toString()).child("comments").addValueEventListener(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    comments.clear()
                    for(data in snapshot.children){
                        val item = data.getValue(Comment::class.java)
                        comments.add(item!!)
                        println(comments)
                    }
                    notifyDataSetChanged()
                    //메세지를 보낼 시 화면을 맨 밑으로 내림
                    recyclerView?.scrollToPosition(comments.size - 1)
                }
            })
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            val view : View = LayoutInflater.from(parent.context).inflate(R.layout.chatroom_item, parent, false)
            return MessageViewHolder(view)
        }
        @SuppressLint("RtlHardcoded")
        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
            holder.message.text = comments[position].message
            holder.time.text = comments[position].time
            if(comments[position].uid.equals(uid)){ // 본인 채팅
                holder.name.visibility = View.GONE
                holder.layout_main.gravity = Gravity.RIGHT
                var color = Color.parseColor("#4682B4")
                holder.cardview.setCardBackgroundColor(color)
                color = Color.parseColor("#FFFFFF")
                holder.message.setTextColor(color)
                color = Color.parseColor("#f1f3f5")
                holder.time.setTextColor(color)
            }else{ // 상대방 채팅
                holder.name.text = destinationUid
                holder.name.visibility = View.VISIBLE
                holder.layout_main.gravity = Gravity.LEFT
                var color = Color.parseColor("#dee2e6")
                holder.cardview.setCardBackgroundColor(color)
            }
        }

        inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val message: TextView = view.findViewById(R.id.chatroom_message)
            val name: TextView = view.findViewById(R.id.chatroom_name)
            val time : TextView = view.findViewById(R.id.chatroom_time)
            val layout_main: LinearLayout = view.findViewById(R.id.chatroom_main)
            val cardview : CardView = view.findViewById(R.id.chatroom_cardview)
        }

        override fun getItemCount(): Int {
            return comments.size
        }
    }
}