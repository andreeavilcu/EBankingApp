package com.example.ebankingapp.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ebankingapp.data.local.transaction.TransactionEntity
import java.util.Locale

@Composable
fun DashboardScreen (
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToTransfer: () -> Unit
){
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF5F5F5),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToTransfer,
                icon = { Icon(Icons.Default.ArrowUpward, contentDescription = null) },
                text = { Text("New transfer") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        }

    ) {
        paddingValues ->

        if(state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Welcome, ${state.account?.holderName?.split(" ")?.firstOrNull() ?: "User"}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                AccountCard(
                    iban = state.account?.iban ?: "",
                    balance = state.account?.balance ?: 0.0,
                    currency = state.account?.currency ?: "RON"
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.transactions) { transaction ->
                        TransactionItem(transaction)
                    }
                }
            }
        }
    }
}

@Composable
fun AccountCard(iban: String, balance: Double, currency: String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF6200EE), Color(0xFF3700B3))
                    )
                )
                .padding(24.dp)
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Current account",
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = String.format(Locale.getDefault(), "%.2f %s", balance, currency),
                    color = Color.White,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = iban,
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium,
                    letterSpacing = 1.sp
                )


            }
        }
    }
}


@Composable
fun TransactionItem(transaction: TransactionEntity){
    val isIncoming = transaction.type == "IN"

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isIncoming) Icons.Filled.ArrowDownward else Icons.Filled.ArrowUpward,
                    contentDescription = null,
                    tint = if (isIncoming) Color(0xFF4CAF50) else Color(0xFFE91E63),
                    modifier = Modifier.size(40.dp)
                        .background(
                            if (isIncoming) Color(0xFFE8F5E9) else Color(0xFFFCE4EC),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = transaction.counterPartyName,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = if (isIncoming) "Incoming" else "Payment",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            Text(
                text = "${if (isIncoming) "+" else "-"} ${transaction.amount} RON",
                fontWeight = FontWeight.Bold,
                color = if (isIncoming) Color(0xFF4CAF50) else Color.Black
            )
        }

    }

}
