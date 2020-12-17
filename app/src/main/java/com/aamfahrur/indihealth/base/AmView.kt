package com.aamfahrur.indihealth.base

import com.aamfahrur.indihealth.utils.interfaces.AmNetworkInterface

interface AmView <T> : AmNetworkInterface {
    var presenter : T?
}