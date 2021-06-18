package exampleapp.mapper.datarow

import com.datastax.oss.driver.api.mapper.annotations.Dao
import com.datastax.oss.driver.api.mapper.annotations.Select
import java.util.concurrent.CompletionStage

@Dao
interface DataRowDao {
    @Select
    fun get(id: String): CompletionStage<DataRow>
}