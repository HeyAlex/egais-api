package ru.evotor.egais.api.model.document.actwriteoff

import ru.evotor.egais.api.model.Version
import ru.evotor.egais.api.model.document.EgaisDocument
import java.util.*

/**
 * Акт списания со склада
 *
 * @property uuid Уникальный идентификатор акта списания со склада
 * @property docOwner Кто подает документы
 * @property identity ИД Акта (клиентский)
 * @property number Номер акта
 * @property actDate Дата составления акта
 * @property typeWriteOff Причина списания
 * @property note Примечание
 * @property status Состояние
 * @property rejectComment Комментарий для отказа на акт списания со склада
 * @property version Версия протокола ЕГАИС
 */
data class ActWriteOff(
        val uuid: UUID,
        val docOwner: String,
        val identity: String?,
        val number: String?,
        val actDate: Date,
        val typeWriteOff: TypeWriteOff?,
        var note: String?,
        var status: ActWriteOffStatus,
        val rejectComment: String?,
        val version: Version
) : EgaisDocument()
