package rs.raf.rmaprojekat1.core.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import rs.raf.rmaprojekat1.R


@Composable
fun DeleteAppIconButton(
    modifier: Modifier = Modifier,
    text: String,
    onDeleteConfirmed: () -> Unit,
) {
    var confirmationDeleteShown by remember { mutableStateOf(false) }
    if (confirmationDeleteShown) {
        AlertDialog(
            onDismissRequest = { confirmationDeleteShown = false },
            title = { Text(text = stringResource(id = R.string.editor_delete_confirmation_title)) },
            text = { Text(text = text) },
            dismissButton = {
                TextButton(onClick = { confirmationDeleteShown = false }) {
                    Text(text = stringResource(id = R.string.app_delete_confirmation_dismiss))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    confirmationDeleteShown = false
                    onDeleteConfirmed()
                }) {
                    Text(text = stringResource(id = R.string.app_delete_confirmation_yes))
                }
            },
        )
    }

    AppIconButton(
        modifier = modifier,
        imageVector = Icons.Default.Delete,
        onClick = { confirmationDeleteShown = true },
    )
}
