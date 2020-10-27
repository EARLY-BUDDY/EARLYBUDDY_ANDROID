package com.earlyBuddy.earlybuddy_android

object TransportMap {
    var jwt: String = ""
    var deviceToken :String = ""

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
        21 to arrayListOf("#6d98d2", "인천1호선"),
        22 to arrayListOf("#f7ac2e", "인천2호선"),
        31 to arrayListOf("#2cb83d", "대전1호선"),
        41 to arrayListOf("#f35f23", "대구1호선"),
        42 to arrayListOf("#2cb83d", "대구2호선"),
        43 to arrayListOf("#fdbe4a", "대구3호선"),
        51 to arrayListOf("#2cb83d", "광주1호선"),
        71 to arrayListOf("#f35f23", "부산1호선"),
        72 to arrayListOf("#2cb83d", "부산2호선"),
        73 to arrayListOf("#d6a64f", "부산3호선"),
        74 to arrayListOf("#3f6db8", "부산4호선"),
        78 to arrayListOf("#a2c4e4", "동해선"),
        79 to arrayListOf("#81469e", "부산-김해경전철"),
        100 to arrayListOf("#d2a715", "분당선"),
        101 to arrayListOf("#d2a715", "공항철도"),
        102 to arrayListOf("#d2a715", "자기부상"),
        104 to arrayListOf("#d2a715", "경의중앙선"),
        107 to arrayListOf("#d2a715", "에버라인"),
        108 to arrayListOf("#d2a715", "경춘선"),
        109 to arrayListOf("#d2a715", "신분당선"),
        110 to arrayListOf("#d2a715", "의정부경전철"),
        111 to arrayListOf("#eeaa00", "수인선"),
        112 to arrayListOf("#1e6ff7", "경강선"),
        113 to arrayListOf("#d2a715", "우이신설"),
        114 to arrayListOf("#8ac832", "서해선"),
        115 to arrayListOf("#977300", "김포골드라인"),
<<<<<<< HEAD
        116 to arrayListOf("#977300", "수인,분당선")

=======
        116 to arrayListOf("#F5A200", "수인분당선")
>>>>>>> ccdeac165cbdf44d531984a2414a52eb6ef09e59
    )

    val busMap: Map<Int, String> = mapOf(
        1 to "#3469ec",
        2 to "#3469ec",
        3 to "#85c900",
        4 to "#ff574c",
        5 to "#70b5e6",
        6 to "#3469ec",
        10 to "#33c63c",
        11 to "#3469ec",
        12 to "#33c63c",
        13 to "#efae13",
        14 to "#ff574c",
        15 to "#ff574c",
        20 to "#FF9DD1",
        21 to "#FF9DD1",
        22 to "#FF9DD1",
        26 to "#3469ec"
    )
}