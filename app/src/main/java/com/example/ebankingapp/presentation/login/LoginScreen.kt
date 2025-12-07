package com.example.ebankingapp.presentation.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val primaryColor = Color(0xFF6200EE)
    val darkColor = Color(0xFF3700B3)

    val biometricAuthenticator = remember { BiometricAuthenticator(context ) }
    val isBiometricAvailable = remember(state.isPinCreated) {
        biometricAuthenticator.isBiometricAvailable() && state.isPinCreated }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(primaryColor, darkColor)
                )
            ),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ){
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = if (state.isPinCreated) "E-banking" else "Welcome to E-banking! Set Up New Account",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Secure and Fast",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = primaryColor,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, CircleShape)
                    .padding(24.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text (
                text = state.message,
                style = MaterialTheme.typography.bodyMedium,
                color = if (state.isError) Color(0xFFFF5252) else Color.White.copy(alpha = 0.8f),
                fontWeight = if (state.isError) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = state.inputPin,
                onValueChange = { newValue ->
                    if(newValue.length > state.inputPin.length){
                        viewModel.onPinEntered(newValue.last().toString(), onLoginSuccess)
                    } else{
                        viewModel.clearInput()
                    }
                },
                label = { Text("PIN") },
                singleLine = true,

                visualTransformation = PasswordVisualTransformation(),

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),

                isError = state.isError,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)

            )


            if (isBiometricAvailable) {
                Spacer(modifier = Modifier.height(24.dp))

                Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                    modifier =Modifier.clickable {
                        val activity = context as? FragmentActivity
                        if (activity != null){
                            biometricAuthenticator.promptBiometricAuth(
                                title = "Biometric Authentication",
                                subTitle = "Authenticate to continue",
                                negativeButtonText = "Cancel",
                                fragmentActivity = activity,
                                onSuccess = {
                                    onLoginSuccess()
                                },
                                onError = { _, errorString ->
                                    Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
                                },
                                onFailed = {
                                    Toast.makeText(context, "Unrecognized fingerprint", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ){
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Fingerprint Login",
                        tint = Color.White,
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape)
                            .padding(12.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Use Fingerprint", color = Color.White.copy(alpha = 0.8f))
                }
            }
        }
    }
}