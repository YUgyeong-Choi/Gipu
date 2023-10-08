package com.example.gipu_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.TreeMap

class ChatListFragment : Fragment() {
    companion object{
        fun newInstance() : ChatListFragment {
            return ChatListFragment()
        }
    }
    private val fireDatabase = FirebaseDatabase.getInstance().reference

    //메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //프레그먼트를 포함하고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    //뷰가 생성되었을 때
    //프레그먼트와 레이아웃을 연결시켜주는 부분
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.chatlist_fragment, container, false)

        val documentBtn: ImageView = view.findViewById(R.id.chatlist_document)
        documentBtn.setOnClickListener {
            val intent = Intent(requireContext(), InfoListActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(0, 0)
        }

        val foodmarketBtn: ImageView = view.findViewById(R.id.chatlist_search)
        foodmarketBtn.setOnClickListener {
            val intent = Intent(requireContext(), FoodbankListActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(0, 0)
        }

        //하단바 프로필 클릭하면 이동
        val profileBtn : ImageView = view.findViewById(R.id.chatlist_profile)
        profileBtn.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(0, 0)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.chatlist_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = RecyclerViewAdapter(requireContext())

        return view
    }

    inner class RecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>() {

        private val chatModel = ArrayList<ChatModel>()
        private var uid : String? = null
        private val destinationUsers : ArrayList<String> = arrayListOf()

        init {
            ProfileActivity.UserDB.init(this.context)
            val userName = ProfileActivity.UserDB.getInstance().getString("user", "")
            uid = userName

            fireDatabase.child("chatrooms").orderByChild("users/$uid").equalTo(true).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatModel.clear()
                    for(data in snapshot.children){
                        chatModel.add(data.getValue(ChatModel::class.java)!!)
                    }
                    notifyDataSetChanged()
                }

            })

        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            return CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.chatlist_item, parent, false))
        }

        inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title : TextView = itemView.findViewById(R.id.chatlist_title)
            val name : TextView = itemView.findViewById(R.id.chatlist_name)
            val lastMessage: TextView = itemView.findViewById(R.id.chatlist_lastword)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            var destinationUid: String? = null
            //채팅방에 있는 유저 모두 체크
            for (user in chatModel[position].users.keys) {
                if (!user.equals(uid)) {
                    destinationUid = user
                    holder.name.text = destinationUid
                    destinationUsers.add(destinationUid)
                }
            }

            //메세지 내림차순 정렬 후 마지막 메세지의 키값을 가져
            val commentMap = TreeMap<String, Comment>(reverseOrder())
            commentMap.putAll(chatModel[position].comments)
            val lastMessageKey = commentMap.keys.toTypedArray()[0]
            holder.lastMessage.text = chatModel[position].comments[lastMessageKey]?.message
            holder.title.text = chatModel[position].comments[lastMessageKey]?.roomName

            //채팅창 선책 시 이동
            holder.itemView.setOnClickListener {
                val intent = Intent(context, ChatRoomActivity::class.java)
                intent.putExtra("destinationUid", destinationUsers[position])
                intent.putExtra("roomName", chatModel[position].comments[lastMessageKey]?.roomName)
                context?.startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            }
        }

        override fun getItemCount(): Int {
            return chatModel.size
        }
    }
}