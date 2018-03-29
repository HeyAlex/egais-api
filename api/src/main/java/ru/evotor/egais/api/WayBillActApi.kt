package ru.evotor.egais.api

import android.content.Context
import ru.evotor.egais.api.model.Version
import ru.evotor.egais.api.model.document.waybillact.*
import ru.evotor.egais.api.provider.Cursor
import ru.evotor.egais.api.provider.converter.QuantityBigDecimalConverter
import ru.evotor.egais.api.provider.waybillact.WayBillActContract
import ru.evotor.egais.api.provider.waybillact.WayBillActPositionContract
import java.util.*

object WayBillActApi {
    @JvmStatic
    fun getWayBillActList(context: Context): ru.evotor.egais.api.provider.Cursor<WayBillAct>? {
        return context.contentResolver.query(WayBillActContract.URI, null, null, null, null)
                ?.let {
                    object : ru.evotor.egais.api.provider.Cursor<WayBillAct>(it) {
                        override fun getValue(): WayBillAct {
                            return createWayBillAct(this)
                        }
                    }
                }
    }


    @JvmStatic
    fun getWayBillActListByDate(context: Context, date: Date): ru.evotor.egais.api.provider.Cursor<WayBillAct>? {
        return context.contentResolver.query(WayBillActContract.URI,
                null, "${WayBillActContract.COLUMN_CREATION_DATE} >= ?", arrayOf(date.toString()), null, null)
                ?.let {
                    object : ru.evotor.egais.api.provider.Cursor<WayBillAct>(it) {
                        override fun getValue(): WayBillAct {
                            return createWayBillAct(this)
                        }
                    }
                }
    }

    private fun createWayBillAct(cursor: Cursor<WayBillAct>): WayBillAct {
        val columnUuid = cursor.getColumnIndexOrThrow(WayBillActContract.COLUMN_UUID)
        val columnOwner = cursor.getColumnIndexOrThrow(WayBillActContract.COLUMN_OWNER)
        val columnIdentity = cursor.getColumnIndexOrThrow(WayBillActContract.COLUMN_IDENTITY)
        val columnAcceptType = cursor.getColumnIndexOrThrow(WayBillActContract.COLUMN_ACCEPT_TYPE)
        val columnNumber = cursor.getColumnIndexOrThrow(WayBillActContract.COLUMN_NUMBER)
        val columnDate = cursor.getColumnIndexOrThrow(WayBillActContract.COLUMN_CREATION_DATE)
        val columnWbRegId = cursor.getColumnIndexOrThrow(WayBillActContract.COLUMN_WB_REG_ID)
        val columnNote = cursor.getColumnIndexOrThrow(WayBillActContract.COLUMN_NOTE)
        val columnType = cursor.getColumnIndexOrThrow(WayBillActContract.COLUMN_TYPE)
        val columnVersion = cursor.getColumnIndexOrThrow(WayBillActContract.COLUMN_VERSION)
        val columnStatus = cursor.getColumnIndexOrThrow(WayBillActContract.COLUMN_STATUS)
        val columnRejectComment = cursor.getColumnIndexOrThrow(WayBillActContract.COLUMN_REJECT_COMMENT)

        return WayBillAct(
                UUID.fromString(cursor.getString(columnUuid)),
                cursor.getString(columnOwner),
                cursor.getString(columnIdentity),
                AcceptType.valueOf(cursor.getString(columnAcceptType)),
                cursor.getString(columnNumber),
                Date(cursor.getString(columnDate)),
                cursor.getString(columnWbRegId),
                cursor.getString(columnNote),
                Type.valueOf(cursor.getString(columnType)),
                Version.valueOf(cursor.getString(columnVersion)),
                Status.valueOf(cursor.getString(columnStatus)),
                cursor.getString(columnRejectComment)
        )
    }

    @JvmStatic
    fun getWayBillActPositionListByUuid(context: Context, uuid: UUID): ru.evotor.egais.api.provider.Cursor<WayBillActPosition>? {
        return context.contentResolver.query(WayBillActPositionContract.URI,
                null, "${WayBillActPositionContract.COLUMN_WAY_BILL_ACT_UUID} = ?", arrayOf(uuid.toString()), null)
                ?.let {
                    object : ru.evotor.egais.api.provider.Cursor<WayBillActPosition>(it) {
                        override fun getValue(): WayBillActPosition {
                            return createWayBillActPosition(this)
                        }
                    }
                }
    }

    private fun createWayBillActPosition(cursor: Cursor<WayBillActPosition>): WayBillActPosition {
        val columnUuid = cursor.getColumnIndex(WayBillActPositionContract.COLUMN_UUID)
        val columnWayBillActUuid = cursor.getColumnIndex(WayBillActPositionContract.COLUMN_WAY_BILL_ACT_UUID)
        val columnIdentity = cursor.getColumnIndex(WayBillActPositionContract.COLUMN_IDENTITY)
        val columnQuantity = cursor.getColumnIndex(WayBillActPositionContract.COLUMN_REAL_QUANTITY)
        val columnInformBRegId = cursor.getColumnIndex(WayBillActPositionContract.COLUMN_INFORM_B_REG_ID)

        return WayBillActPosition(
                UUID.fromString(cursor.getString(columnUuid)),
                UUID.fromString(cursor.getString(columnWayBillActUuid)),
                cursor.getString(columnIdentity),
                cursor.getString(columnInformBRegId),
                QuantityBigDecimalConverter.toBigDecimal(cursor.getLong(columnQuantity))
        )
    }
}