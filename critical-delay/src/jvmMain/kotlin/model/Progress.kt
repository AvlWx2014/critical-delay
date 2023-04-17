package model

typealias ProgressCallback = (Progress) -> Unit

interface Progress

interface LabeledProgress {
    val label: String
} 

interface DeterminateProgress : Progress {
    val progress: Float
    val outOf: Float
}

private data class DeterminateProgressImpl(
    override val progress: Float,
    override val outOf: Float
) : DeterminateProgress
fun determinateProgress(progress: Float, outOf: Float): DeterminateProgress = DeterminateProgressImpl(progress, outOf)
fun determinateProgress(progress: Int, outOf: Int): DeterminateProgress =
    DeterminateProgressImpl(progress.toFloat(), outOf.toFloat())

private data class DeterminateLabeledProgress(
    override val progress: Float,
    override val outOf: Float,
    override val label: String
) : DeterminateProgress, LabeledProgress

fun determinateLabeledProgress(progress: Float, outOf: Float, label: String): DeterminateProgress =
    DeterminateLabeledProgress(progress, outOf, label)
fun determinateLabeledProgress(progress: Int, outOf: Int, label: String): DeterminateProgress =
    DeterminateLabeledProgress(progress.toFloat(), outOf.toFloat(), label)


val DeterminateProgress.isDone: Boolean
    get() = progress >= outOf


val DeterminateProgress.ratio: Float
    get() = progress / outOf

val DeterminateProgress.percentage: Float
    get() = ratio * 100f

fun done(total: Int) = done(total.toFloat())
fun done(total: Float = 1f) = determinateProgress(total, total)

fun zero(): DeterminateProgress = determinateProgress(0, 1)

interface IndeterminateProgress : Progress

private object IndeterminateProgressImpl : IndeterminateProgress

fun indeterminateProgress(): IndeterminateProgress = IndeterminateProgressImpl
private data class IndeterminateLabeledProgress(
    override val label: String
) : IndeterminateProgress, LabeledProgress

fun indeterminateLabeledProgress(label: String): IndeterminateProgress = IndeterminateLabeledProgress(label)

