package org.goldenage.com.goldenage

import android.content.SharedPreferences
import com.github.mikephil.charting.data.Entry
import org.json.JSONArray
import org.json.JSONObject

class LifeEventController(preference : SharedPreferences)
{
    var _lifeEvents : ArrayList<LifeEvent>? = null
    var _lifeEventEntrys : MutableList<Entry>? = null
    var _preference : SharedPreferences? = null

    init {
        _preference = preference
    }

    fun getLifeEvents() : ArrayList<LifeEvent>?
    {
        return _lifeEvents
    }

    fun getLifeEventEntrys() : MutableList<Entry>?
    {
        return _lifeEventEntrys
    }

    fun getMin() : Int
    {
        if (_lifeEvents!!.isEmpty())
            return 0

        var event = _lifeEvents!!.first()
        return event.age * 12 + event.month
    }

    fun getMax() : Int
    {
        if (_lifeEvents!!.isEmpty())
            return 10

        var event = _lifeEvents!!.last()
        return event.age * 12 + event.month
    }

    /**
     * 이벤트를 정렬함
     */
    fun alignmentEntryEvents()
    {
        if (_lifeEventEntrys == null)
            return

        _lifeEventEntrys!!.sortWith(object: Comparator<Entry>{
            override fun compare(o1: Entry?, o2: Entry?): Int = when{
                o1!!.x > o2!!.x -> 1
                else -> -1
            }
        })
    }

    /**
     * 이벤트를 정렬함
     */
    fun alignmentEvents()
    {
        if (_lifeEvents == null)
            return

        _lifeEvents!!.sortWith(object: Comparator<LifeEvent>{
            override fun compare(o1: LifeEvent?, o2: LifeEvent?): Int = when{
                (o1!!.age * 12 + o1!!.month) > (o2!!.age * 12 + o2!!.month) -> 1
                else -> -1
            }
        })
    }

    /**
     * 로컬에 저장된 이벤트를 불러온다
     * @return List<LifeEvent>? 로드된 이벤트 목록
     */
    public fun loadLifeEvents()
    {
        var jsonData = _preference!!.getString("json", "")
        jsonData = ""
        if (jsonData == "")
        {
            addLifeEvent(LifeEvent(1,10,"내가 태어남", 50))
            addLifeEvent(LifeEvent(3,5,"첫 걸음마", 75))
            saveToJSON()
        }
        else
        {
            jsonToEvent(jsonData)
        }
    }

    /**
     * 이벤트를 JSON 데이터화 시킨다
     */
    public fun saveToJSON()
    {
        var jsonArray = JSONArray()
        for (event in _lifeEvents!!)
        {
            var eventJson = JSONObject()
            eventJson.put("age", event.age)
            eventJson.put("month", event.month)
            eventJson.put("desc", event.desc)
            eventJson.put("satisfaction", event.satisfaction)
            jsonArray.put(eventJson)
        }
        var root = JSONObject()
        root.put("array", jsonArray)
        _preference!!.edit().putString("json", root.toString()).commit()
    }

    /**
     * JSON 데이터를 이벤트로 변경한다
     */
    public fun jsonToEvent(jsonData : String)
    {
        var root  = JSONObject(jsonData)
        var array = root.getJSONArray("array")
        for (i in 0..(array.length() - 1))
        {
            var json = array.getJSONObject(i)
            addLifeEvent(LifeEvent(json.getInt("age"), json.getInt("month"), json.getString("desc"), json.getInt("satisfaction")))
        }
    }

    /**
     * 이벤트를 추가한다
     * @param LifeEvent 이벤트 데이터
     */
    public fun addLifeEvent(lifeEvent : LifeEvent)
    {
        if (_lifeEvents == null)
        {
            _lifeEvents = arrayListOf()
            _lifeEventEntrys = mutableListOf()
        }

        var x = lifeEvent.age.toFloat() * 12 + lifeEvent.month.toFloat()
        var y = lifeEvent.satisfaction.toFloat()

        _lifeEvents!!.add(lifeEvent)
        _lifeEventEntrys!!.add(Entry(x, y))
        alignmentEvents()
        alignmentEntryEvents()

        saveToJSON()
    }

    /**
     * 이벤트를 삭제한다
     * @param Int 삭제할 인덱스
     */
    public fun removeEvent(position : Int)
    {
        if (_lifeEvents != null && !_lifeEvents!!.isEmpty())
        {
            _lifeEvents!!.removeAt(position)
            _lifeEventEntrys!!.removeAt(position)

            saveToJSON()
        }
    }

}