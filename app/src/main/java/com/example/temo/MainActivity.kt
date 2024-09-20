package com.example.temo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import com.example.temo.ui.theme.TemoTheme
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(getString(R.string.default_web_client_id))
            .setAutoSelectEnabled(false)
            .build()

//        enableEdgeToEdge()
        setContent {
            TemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val contextAct = LocalContext.current as Activity?
                    val context = LocalContext.current

                    val request: GetCredentialRequest = GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .build()

                    val coroutineScope = rememberCoroutineScope()
                    val credentialManager = CredentialManager.create(context)

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)
                        )
                        GoogleLoginButton(onLoginClick = {
                            coroutineScope.launch {
                                try {
                                    val result = credentialManager.getCredential(
                                        request = request,
                                        context = contextAct!! // Activity context 사용
                                    )
                                    handleSignIn(result, contextAct) // 로그인 성공 처리
                                    val intent = Intent(contextAct, HomeActivity::class.java)
                                    contextAct.startActivity(intent)
                                } catch (e: GetCredentialException) {
                                    handleFailure(e) // 실패 처리
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}

private fun handleSignIn(
    result: GetCredentialResponse,
    contextAct: Activity
) {
    when (val credential = result.credential) {
        // Passkey 자격 증명
        is PublicKeyCredential -> {
            // responseJson을 서버로 보내어 유효성 검증 및 인증을 수행합니다.
            val responseJson = credential.authenticationResponseJson
            // 서버에서 이 JSON을 사용해 검증 로직을 수행해야 합니다.
        }

        // 비밀번호 자격 증명
        is PasswordCredential -> {
            // ID와 비밀번호를 서버로 보내어 유효성 검증 및 인증을 수행합니다.
            val username = credential.id
            val password = credential.password
            // 서버로 ID와 비밀번호를 보내어 검증 로직을 수행해야 합니다.
        }

        is CustomCredential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    // GoogleIdTokenCredential을 사용하여 ID 토큰을 추출하고 이를 검증합니다.
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken

                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(firebaseCredential)
                        .addOnCompleteListener(contextAct) { task ->
                            if (task.isSuccessful) {
                                // 로그인 성공
                                val user = FirebaseAuth.getInstance().currentUser
                                Log.d(
                                    "MainActivity",
                                    "signInWithCredential:success - user: ${user?.displayName}"
                                )
                            } else {
                                // 로그인 실패
                                Log.w(
                                    "MainActivity",
                                    "signInWithCredential:failure",
                                    task.exception
                                )
                            }
                        }


                } catch (e: GoogleIdTokenParsingException) {
                    Log.e("MainActivity", "Received an invalid google id token response", e)
                }
            } else {
                // 인식되지 않은 자격 증명 유형 처리
                Log.e("MainActivity", "Unexpected type of credential")
            }
        }
        else -> {
            // 인식되지 않은 자격 증명 유형 처리
            Log.e("MainActivity", "Unexpected type of credential")
        }
    }
}

private fun handleFailure(exception: GetCredentialException) {
    // 실패 처리 로직
    Log.e("MainActivity", "GetCredential failed", exception)
}

@Composable
fun GoogleLoginButton(onLoginClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.google_continue),
            contentDescription = "google_continue",
            modifier = Modifier
                .scale(3f)
                .clickable {
                    onLoginClick()
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TemoTheme {
    }
}