package com.example.neven.conversionapp.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

import java.io.Serializable

@Entity(tableName = "rates")
@Parcelize
data class ExchangeRate(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @SerializedName("median_rate")
    @Expose
    var medianRate: String? = null,

    @SerializedName("unit_value")
    @Expose
    var unitValue: Int? = null,

    @SerializedName("selling_rate")
    @Expose
    var sellingRate: String? = null,

    @SerializedName("currency_code")
    @Expose
    var currencyCode: String? = null,

    @SerializedName("buying_rate")
    @Expose
    var buyingRate: String? = null

) : Parcelable




