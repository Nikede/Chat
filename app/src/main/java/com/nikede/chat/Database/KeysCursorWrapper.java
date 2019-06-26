package com.nikede.chat.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

public class KeysCursorWrapper extends CursorWrapper {

    public KeysCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public int getKey() {
        String key = getString(getColumnIndex(KeysSchema.KeysTable.Cols.sKey));

        return Integer.parseInt(key);
    }

    static public KeysCursorWrapper queryKeys(Context context, String whereClause, String[] whereArgs) {
        Cursor cursor = new KeysBaseHelper(context).getWritableDatabase().query(
                KeysSchema.KeysTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new KeysCursorWrapper(cursor);
    }
}
