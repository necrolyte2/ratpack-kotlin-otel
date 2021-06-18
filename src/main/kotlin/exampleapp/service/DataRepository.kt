package exampleapp.service

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import com.google.inject.Inject
import exampleapp.mapper.DataRowMapper
import exampleapp.mapper.datarow.DataRow
import ratpack.exec.Promise
import ratpack.service.Service
import ratpack.service.StartEvent
import ratpack.service.StopEvent
import java.util.concurrent.CompletionStage
import javax.xml.crypto.Data

class DataRepository @Inject constructor(
    private val cqlSession: CqlSession
): Service {
    override fun onStart(event: StartEvent) {
        cqlSession.execute(
            SimpleStatement.newInstance(
        "CREATE KEYSPACE IF NOT EXISTS datarepo WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}"
            )
        )
        cqlSession.execute("USE datarepo")
        cqlSession.execute(
            SimpleStatement.newInstance(
        "CREATE TABLE IF NOT EXISTS datarow (" +
                "  id text," +
                "  value text," +
                "  PRIMARY KEY (id)" +
                ");"
            )
        )
        cqlSession.execute(
            SimpleStatement.newInstance(
                "INSERT INTO datarow (id, value) VALUES ('1', 'testing') IF NOT EXISTS"
            )
        )
    }

    override fun onStop(event: StopEvent) {
        cqlSession.close()
    }

    fun toDataRowPromise(completionStage: CompletionStage<DataRow>): Promise<DataRow> {
        return Promise.async<DataRow> { upstream ->
            upstream.accept(completionStage)
        }
    }

    fun get(id: String): Promise<DataRow> {
        val mapper: DataRowMapper = DataRowMapper.builder(cqlSession).withDefaultKeyspace("datarepo").build()
        val dao = mapper.dataRowDao()
        return toDataRowPromise(dao.get(id))
    }
}