package pl.manfredmaniek.audioplaybackdetector

/**
 * The Yamaha R-N602 supports the following inputs.
 *
 * To get a list of supported inputs for other Yamaha MusicCast enabled receivers, call:
 *
 * YamahaExtendedControl/v1/system/getFeatures
 *
 */
object Input {
    const val NAPSTER_INPUT_ID = "napster"
    const val SPOTIFY_INPUT_ID = "spotify"
    const val JUKE_INPUT_ID = "juke"
    const val QOBUZ_INPUT_ID = "qobuz"
    const val TIDAL_INPUT_ID = "tidal"
    const val DEEZER_INPUT_ID = "deezer"
    const val AIRPLAY_INPUT_ID = "airplay"
    const val MC_LINK_INPUT_ID = "mc_link"
    const val SERVER_INPUT_ID = "server"
    const val NET_RADIO_INPUT_ID = "net_radio"
    const val BLUETOOTH_INPUT_ID = "bluetooth"
    const val USB_INPUT_ID = "usb"
    const val TUNER_INPUT_ID = "tuner"
    const val OPTICAL1_INPUT_ID = "optical1"
    const val OPTICAL2_INPUT_ID = "optical2"
    const val COAXIAL1_INPUT_ID = "coaxial1"
    const val COAXIAL2_INPUT_ID = "coaxial2"
    const val LINE1_INPUT_ID = "line1"
    const val LINE2_INPUT_ID = "line2"
    const val LINE3_INPUT_ID = "line3"
    const val LINE_CD_INPUT_ID = "line_cd"
    const val PHONO_INPUT_ID = "phono"
}
