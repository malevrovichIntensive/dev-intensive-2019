package ru.skillbranch.devintensive.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG
import kotlinx.android.synthetic.main.ac_main.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter
import ru.skillbranch.devintensive.ui.adapters.ChatItemTouchHelperCallback
import ru.skillbranch.devintensive.ui.group.GroupActivicty
import ru.skillbranch.devintensive.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        initToolbar()
        initViews()
        initViewModel()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initViews() {
        chatAdapter = ChatAdapter()

        val callback = ChatItemTouchHelperCallback(chatAdapter,
            {
                with(it as ChatItem) {// SWIPE TO START
                    viewModel.addToArchive(id)
                    Snackbar.make(rv_chat_list, "Чат ${it.title} был заархивирован", LENGTH_LONG)
                        .setAction("UNDO"){
                            viewModel.restoreFromArchive(id)
                        }.show()
                }
            }, {
                with(it as ChatItem) {// SWIPE TO END
                    val ind = viewModel.remove(id)
                    Snackbar.make(rv_chat_list, "Чат ${it.title} был удален", LENGTH_LONG)
                        .setAction("UNDO"){
                            viewModel.restoreLastDeleted()
                        }.show()
                }
            })
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(rv_chat_list)

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        with(rv_chat_list) {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(divider)
        }

        fab.setOnClickListener{
            val intent = Intent(this, GroupActivicty::class.java)
            startActivity(intent)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getChatData().observe(this, Observer { chatAdapter.updateData(it) })
    }
}