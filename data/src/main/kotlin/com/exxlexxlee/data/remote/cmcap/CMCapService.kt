package com.exxlexxlee.data.remote.cmcap

import com.exxlexxlee.domain.model.TokenData


interface CMCapService {
    suspend fun topTokens(): List<TokenData>
}