package krafts.alex.backupgram.ui.messages

import android.graphics.Color
import android.opengl.Visibility
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.item_message.view.*
import krafts.alex.backupgram.ui.R
import krafts.alex.backupgram.ui.utils.CircleTransform
import krafts.alex.tg.entity.Message
import java.io.File

class MessagesAdapter(
    private var values: List<Message>
) : RecyclerView.Adapter<MessageViewHolder>() {

    fun setAll(items: List<Message>) {
        values = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val item = values[position]

        holder.name.text = item.user?.let { it.firstName + " " + it.lastName }

        item.user?.photoBig?.let {
            if (it.downloaded)
                Picasso
                    .get()
                    .load(File(it.localPath))
                    .transform(CircleTransform())
                    .into(holder.avatar)
        }

        holder.message.text = item.text

        if (position != 0 && item.senderId == values[position - 1].senderId) {
            holder.name.visibility = View.GONE
            holder.avatar.setColorFilter(Color.WHITE)
        }
        with(holder.view) {
            tag = item
        }
    }

    override fun getItemCount(): Int = values.size
}

class MessageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.name
    val message: TextView = view.message
    val avatar: ImageView = view.avatar

    override fun toString(): String {
        return super.toString() + " '" + message.text + "'"
    }
}

