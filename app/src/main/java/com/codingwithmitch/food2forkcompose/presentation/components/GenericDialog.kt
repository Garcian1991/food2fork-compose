package com.codingwithmitch.food2forkcompose.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class PositiveAction private constructor(
    builder: Builder
) {
    val positiveButtonText = builder.positiveButtonText
    val onPositiveAction = builder.onPositiveAction

    class Builder {
        var positiveButtonText: String? = null
        var onPositiveAction: (() -> Unit)? = null

        fun build() = PositiveAction(this)
    }

    companion object {
        fun PositiveAction(init: PositiveAction.Builder.() -> Unit) =
            Builder()
                .also(init)
                .build()
    }
}

data class NegativeAction(
    val negativeButtonText: String,
    val onNegativeAction: () -> Unit
)


fun GenericDialogInfo(
    init: GenericDialogInfo.Builder.() -> Unit
): GenericDialogInfo = GenericDialogInfo.Builder()
    .also(init)
    .build()


class GenericDialogInfo private constructor(
    builder: Builder
) {
    val title: String
    val onDismiss: () -> Unit
    val description: String?
    val positiveAction: PositiveAction?
    val negativeAction: NegativeAction?

    init {
        if (builder.title == null) {
            throw NullPointerException("GenericDialogInfo title cannot be null.")
        }
        if (builder.onDismiss == null) {
            throw NullPointerException("GenericDialogInfo onDismiss cannot be null.")
        }
        this.title = builder.title!!
        this.onDismiss = builder.onDismiss!!
        this.description = builder.description
        this.positiveAction = builder.positiveAction
        this.negativeAction = builder.negativeAction
    }

    class Builder {
        var title: String? = null
        var onDismiss: (() -> Unit)? = null
        var description: String? = null
        var positiveAction: PositiveAction? = null
        var negativeAction: NegativeAction? = null

//        fun title(title: String): Builder {
//            this.title = title
//            return this
//        }
//
//        fun onDismiss(onDismiss: () -> Unit): Builder {
//            this.onDismiss = onDismiss
//            return this
//        }
//
//        fun description(description: String): Builder {
//            this.description = description
//            return this
//        }
//
//        fun positiveAction(positiveAction: PositiveAction): Builder {
//            this.positiveAction = positiveAction
//            return this
//        }
//
//        fun negativeAction(negativeAction: NegativeAction): Builder {
//            this.negativeAction = negativeAction
//            return this
//        }

        fun build() = GenericDialogInfo(this)
    }
}

@Composable
fun GenericDialog(
    modifier: Modifier,
    onDismiss: () -> Unit,
    title: String,
    description: String? = null,
    positiveAction: PositiveAction? = null,
    negativeAction: NegativeAction? = null
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onDismiss()
        },
        title = { Text(text = title) },
        text = {
            if (description != null) {
                Text(description)
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (negativeAction != null) {
                    Button(
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onError),
                        onClick = negativeAction.onNegativeAction
                    ) {
                        Text(negativeAction.negativeButtonText)
                    }
                }
                if (positiveAction != null) {
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = positiveAction.onPositiveAction!!
                    ) {
                        Text(positiveAction.positiveButtonText!!)
                    }
                }
            }
        }
    )
}
