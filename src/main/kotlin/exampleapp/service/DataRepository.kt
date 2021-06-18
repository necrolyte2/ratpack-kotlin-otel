package exampleapp.service

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import com.google.inject.Inject
import exampleapp.mapper.DataRowMapper
import exampleapp.mapper.datarow.DataRow
import ratpack.service.Service
import ratpack.service.StartEvent
import ratpack.service.StopEvent

class DataRepository @Inject constructor(
    private val cqlSession: CqlSession
): Service {
    override fun onStart(event: StartEvent) {
        cqlSession.execute(
            SimpleStatement.newInstance(
        "CREATE KEYSPACE IF NOT EXISTS datarepo WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}"
            )
        )
        cqlSession.execute(
            SimpleStatement.newInstance(
        "CREATE TABLE IF NOT EXISTS datarow (" +
                "  id text," +
                "  value text," +
                "  PRIMARY KEY (id)" +
                ");"
            )
        )
    }

    override fun onStop(event: StopEvent) {
        cqlSession.close()
    }

    fun  get(id: String): DataRow {
        val mapper: DataRowMapper = DataRowMapper.builder(cqlSession).withDefaultKeyspace("datarepo").build()
        val dao = mapper.dataRowDao()
        return dao.get(id)
    }
}