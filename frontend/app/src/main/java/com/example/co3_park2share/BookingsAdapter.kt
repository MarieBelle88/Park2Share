package com.example.co3_park2share

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookingsAdapter(private val bookings: List<Booking>) :
    RecyclerView.Adapter<BookingsAdapter.BookingViewHolder>() {

    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carNameTextView: TextView = itemView.findViewById(R.id.carNameTextView)
        val dateRangeTextView: TextView = itemView.findViewById(R.id.dateRangeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]
        holder.carNameTextView.text = booking.carName
        holder.dateRangeTextView.text = "${booking.startDate} - ${booking.endDate}"
    }

    override fun getItemCount(): Int = bookings.size
}
