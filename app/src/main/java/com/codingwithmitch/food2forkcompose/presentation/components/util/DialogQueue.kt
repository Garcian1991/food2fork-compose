package com.codingwithmitch.food2forkcompose.presentation.components.util

import androidx.compose.runtime.mutableStateListOf
import com.codingwithmitch.food2forkcompose.presentation.components.GenericDialogInfo
import com.codingwithmitch.food2forkcompose.presentation.components.PositiveAction.Companion.PositiveAction
import java.util.*

class DialogQueue {
    val queue = mutableStateListOf<GenericDialogInfo>()

    private fun removeHeadMessage() {
        if (queue.isNotEmpty()) {
            queue.removeFirst()
        }
    }

    fun appendErrorMessage(title: String, description: String) {
        queue.add(
            GenericDialogInfo {
                this.title = title
                this.description = description
                onDismiss = this@DialogQueue::removeHeadMessage
                positiveAction = PositiveAction {
                    positiveButtonText = "Ok"
                    onPositiveAction = this@DialogQueue::removeHeadMessage
                }
            }
        )
    }
}