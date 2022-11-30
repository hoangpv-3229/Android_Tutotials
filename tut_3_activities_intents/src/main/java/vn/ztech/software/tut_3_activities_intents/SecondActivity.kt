package vn.ztech.software.tut_3_activities_intents

class SecondActivity : MainActivity() {
    override val LAYOUT: Int
        get() = R.layout.activity_second
    override val OTHER_ACTIVITY: Any
        get() = MainActivity::class.java
}