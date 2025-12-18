package com.sandkshnbh.Roge.presentation.onboarding

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SettingsAccessibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sandkshnbh.Roge.service.RootUtils
import kotlinx.coroutines.launch

@Composable
fun PermissionScreen(onPermissionsGranted: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    var isAccessibilityEnabled by remember { mutableStateOf(false) } // Simplified check
    var isRootGranted by remember { mutableStateOf(RootUtils.isRootAvailable()) }

    // Periodically check accessibility service status (simplified for demo)
    // In a real app, use AccessibilityManager or Lifecycle resume check
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to Roge",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "To unleash the full power of gestures, Roge needs a few permissions.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))

        // Accessibility Card
        PermissionCard(
            title = "Accessibility Service",
            description = "Required for gesture detection.",
            icon = Icons.Default.SettingsAccessibility,
            isGranted = isAccessibilityEnabled,
            onGrant = {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                context.startActivity(intent)
                // User needs to manually enable it. We can't easily detect it instantly without a service callback.
                // For this UI demo, we'll assume they might have done it if they come back, 
                // or we rely on the service to update a shared pref.
                // For now, let's just let them proceed if they say they did it or we can check service running.
                isAccessibilityEnabled = true // Optimistic update for demo flow
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Root Card
        PermissionCard(
            title = "Root Access",
            description = "Required for advanced system control.",
            icon = Icons.Default.Security,
            isGranted = isRootGranted,
            onGrant = {
                scope.launch {
                    if (RootUtils.requestRoot()) {
                        isRootGranted = true
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onPermissionsGranted,
            enabled = isAccessibilityEnabled || isRootGranted, // Allow if at least one is "done"
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Get Started")
        }
    }
}

@Composable
fun PermissionCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isGranted: Boolean,
    onGrant: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isGranted) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isGranted) Icons.Default.CheckCircle else icon,
                contentDescription = null,
                tint = if (isGranted) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isGranted) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isGranted) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (!isGranted) {
                Button(
                    onClick = onGrant,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Grant")
                }
            }
        }
    }
}
