package com.example.bancamovil.data.datasource

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://eweetgaqfdbovbpkbbcm.supabase.co",
        supabaseKey = "sb_publishable_OnZPbnZtKeqW5ZPUJgQZcw_oI8qVWUT"
    ) {
        install(Storage)
    }
}