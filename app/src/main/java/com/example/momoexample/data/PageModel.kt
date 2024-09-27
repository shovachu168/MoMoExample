package com.example.momoexample.datamodel

import com.google.gson.annotations.SerializedName

data class PageModel(
    @SerializedName("promote_content") var promoteList: List<String>,
    @SerializedName("products") var productList: List<ProductModel>,
)

data class ProductModel(
    @SerializedName("product_id") var id: Int,
    @SerializedName("product_name") var name: String,
    @SerializedName("is_like") var isLike: Boolean
)