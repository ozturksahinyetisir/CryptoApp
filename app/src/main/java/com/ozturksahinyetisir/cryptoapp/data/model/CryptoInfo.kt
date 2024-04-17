package com.ozturksahinyetisir.cryptoapp.data.model

data class CryptoInfo(
    val id: Int,
    val name: String,
    val symbol: String,
    val slug: String,
    val num_market_pairs: Int,
    val date_added: String,
    val tags: List<String>,
    val max_supply: Double?,
    val circulating_supply: Double,
    val total_supply: Double,
    val infinite_supply: Boolean,
    val platform: Platform?,
    val cmc_rank: Int,
    val self_reported_circulating_supply: Double?,
    val self_reported_market_cap: Double?,
    val tvl_ratio: Double?,
    val last_updated: String,
    val quote: Quote
)

data class Platform(
    val id: Int,
    val name: String,
    val symbol: String,
    val slug: String,
    val token_address: String
)

data class Quote(
    val USD: QuoteDetails
)

data class QuoteDetails(
    val price: Double,
    val volume_24h: Double,
    val volume_change_24h: Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_7d: Double,
    val percent_change_30d: Double,
    val percent_change_60d: Double,
    val percent_change_90d: Double,
    val market_cap: Double,
    val market_cap_dominance: Double,
    val fully_diluted_market_cap: Double,
    val tvl: Double?,
    val last_updated: String
)

data class CryptoInfoResponse(
    val status: Status,
    val data: List<CryptoInfo>
)

data class Status(
    val timestamp: String,
    val error_code: Int,
    val error_message: String?,
    val elapsed: Int,
    val credit_count: Int,
    val notice: String?,
    val total_count: Int
)