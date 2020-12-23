##Collection of usually used snippets 

###Utility

**Show timer into Kotlin**

```
Timer().schedule(3500) {
        hideProgressDialog()
    }
```

###UI

**Make Dialog Transparent BG**

```dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))```

**Launch app settings screen**

```    private fun openAppSettings() {
           Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
               addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
               data = Uri.fromParts("package", packageName, null)
               startActivity(this)
           }
       }
```
                
