package com.earlyBuddy.earlybuddy_android

object TransportMap {
    val subwayMap: Map<Int, ArrayList<String>> = mapOf(
        1 to arrayListOf("#243899", "1호선"),
        2 to arrayListOf("#35b645", "2호선"),
        3 to arrayListOf("#f36e00", "3호선"),
        4 to arrayListOf("#219de2", "4호선"),
        5 to arrayListOf("#8828e2", "5호선"),
        6 to arrayListOf("#b75000", "6호선"),
        7 to arrayListOf("#697305", "7호선"),
        8 to arrayListOf("#e8146d", "8호선"),
        9 to arrayListOf("#d2a715", "9호선"),
        100 to arrayListOf("#d2a715", "분당선"),
        101 to arrayListOf("#d2a715", "공항철도"),
        102 to arrayListOf("#d2a715", "자기부상"),
        104 to arrayListOf("#d2a715", "경의중앙선"),
        107 to arrayListOf("#d2a715", "에버라인"),
        108 to arrayListOf("#d2a715", "경춘선"),
        109 to arrayListOf("#d2a715", "신분당선"),
        110 to arrayListOf("#d2a715", "의정부경전철"),
        113 to arrayListOf("#d2a715", "우이신설")
    )

    val busMap: Map<Int, String> = mapOf(
        1 to "#3469ec",
        2 to "#3469ec",
        11 to "#3469ec",
        10 to "#33c63c",
        12 to "#33c63c",
        4 to "#ff574c",
        14 to "#ff574c",
        15 to "#ff574c",
        5 to "#70b5e6"
    )
}