package com.j.ming.duttrade.model.local_data

/**
 * Created by sunny on 18-1-27.
 */

open class LocalFileInfo(val name: String, val size: Long, val lastModifiedTime: Long,
                         val path: String, val isDirect: Boolean)