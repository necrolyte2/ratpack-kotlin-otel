package exampleapp.mapper

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.mapper.MapperBuilder
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory
import com.datastax.oss.driver.api.mapper.annotations.Mapper
import exampleapp.mapper.datarow.DataRowDao

@Mapper
interface DataRowMapper {

    @DaoFactory
    fun dataRowDao(): DataRowDao

    companion object {
        fun builder(session: CqlSession): MapperBuilder<DataRowMapper> {
            return DataRowMapperBuilder(session)
        }
    }
}