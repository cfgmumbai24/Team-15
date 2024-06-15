package jpmc.team15.userapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jpmc.team15.userapp.R
import jpmc.team15.userapp.databinding.ItemBoardBinding
import jpmc.team15.userapp.models.Board

open class ItemBoardAdapter(
    private val context: Context,
    private val list: ArrayList<Board>
):
    RecyclerView.Adapter<ItemBoardAdapter.MainViewHolder>()
{
        inner class MainViewHolder(val itemBinding: ItemBoardBinding):RecyclerView.ViewHolder(itemBinding.root){
        //bind the data to the card
        fun bindItem(model:Board){
            itemBinding.tvCategoryName.text =model.category
            itemBinding.tvDate.text =model.date
            Glide.with(context)
                .load(model.image)
                .fitCenter()
                .placeholder(R.drawable.ic_board_place_holder)
                .into(itemBinding.ivBoardImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(ItemBoardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val model=list[position]
        holder.bindItem(model)
    }
}
