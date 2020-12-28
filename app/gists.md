### Collection of usually used snippets


<details>
<summary>Utility</summary>
<br>
<br>
Show timer into Kotlin

```
Timer().schedule(3500) {
        hideProgressDialog()
    }
```

**Create a Single View ClickListener**

```
class OnSingleClickListener : View.OnClickListener {

    private val onClickListener: View.OnClickListener

    constructor(listener: View.OnClickListener) {
        onClickListener = listener
    }

    constructor(listener: (View) -> Unit) {
        onClickListener = View.OnClickListener { listener.invoke(it) }
    }

    override fun onClick(v: View) {
        val currentTimeMillis = System.currentTimeMillis()

        if (currentTimeMillis >= previousClickTimeMillis + DELAY_MILLIS) {
            previousClickTimeMillis = currentTimeMillis
            onClickListener.onClick(v)
        }
    }

    companion object {
        // Tweak this value as you see fit. In my personal testing this
        // seems to be good, but you may want to try on some different
        // devices and make sure you can't produce any crashes.
        private const val DELAY_MILLIS = 200L

        private var previousClickTimeMillis = 0L
    }

}

```

```
fun View.setOnSingleClickListener(l: View.OnClickListener) {
    setOnClickListener(OnSingleClickListener(l))
}

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}
```

</details>


<details>
<summary>UI</summary>
<br>
<br>
Make Dialog Transparent BG

```dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))```

**Launch app settings screen**

```
    private fun openAppSettings() {
           Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
               addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
               data = Uri.fromParts("package", packageName, null)
               startActivity(this)
           }
       }
```

</details>

                
