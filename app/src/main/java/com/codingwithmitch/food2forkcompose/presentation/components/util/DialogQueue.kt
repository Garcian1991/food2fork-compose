package com.codingwithmitch.food2forkcompose.presentation.components.util

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.codingwithmitch.food2forkcompose.presentation.components.GenericDialogInfo
import com.codingwithmitch.food2forkcompose.presentation.components.PositiveAction.Companion.PositiveAction
import java.util.*

class StateQueue<T>(
    private val list: SnapshotStateList<T>
) : Queue<T> {
    override fun add(element: T): Boolean {
        return list.add(element = element)
    }

    override val size: Int
        get() = TODO("Not yet implemented")

    override fun addAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun iterator(): MutableIterator<T> {
        TODO("Not yet implemented")
    }

    override fun remove(): T {
        return list.removeFirst()
    }

    override fun contains(element: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    override fun remove(element: T): Boolean {
        return list.remove(element = element)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun offer(p0: T): Boolean {
        return list.add(p0)
    }

    override fun poll(): T? {
        TODO("Not yet implemented")
    }

    override fun element(): T {
        TODO("Not yet implemented")
    }

    override fun peek(): T? {
        return list.firstOrNull()
    }

}

class DialogQueue {
    val queue: StateQueue<GenericDialogInfo> = StateQueue(mutableStateListOf())

    private fun removeHeadMessage() {
        if (queue.isNotEmpty()) {
            queue.remove()
        }
    }

    fun appendErrorMessage(title: String, description: String) {
        queue.offer(
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