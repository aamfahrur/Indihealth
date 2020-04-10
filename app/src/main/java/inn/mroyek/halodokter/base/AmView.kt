package inn.mroyek.halodokter.base

import inn.mroyek.halodokter.utils.interfaces.AmNetworkInterface

interface AmView <T> : AmNetworkInterface {
    var presenter : T?
}