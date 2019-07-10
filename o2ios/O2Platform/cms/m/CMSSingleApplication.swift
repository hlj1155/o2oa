//
//  CMSSingleApplication.swift
//  O2Platform
//
//  Created by FancyLou on 2019/7/8.
//  Copyright © 2019 zoneland. All rights reserved.

import Foundation
import ObjectMapper


class CMSSingleApplication : NSObject, NSCoding, Mappable{
    
    var count : Int?
    var data : CMSData?
    var date : String?
    var message : String?
    var position : Int?
    var size : Int?
    var spent : Int?
    var type : String?
    var userMessage : String?
    
    
    class func newInstance(map: Map) -> Mappable?{
        return CMSSingleApplication()
    }
    required init?(map: Map){}
    private override init(){}
    
    func mapping(map: Map)
    {
        count <- map["count"]
        data <- map["data"]
        date <- map["date"]
        message <- map["message"]
        position <- map["position"]
        size <- map["size"]
        spent <- map["spent"]
        type <- map["type"]
        userMessage <- map["userMessage"]
        
    }
    
    /**
     * NSCoding required initializer.
     * Fills the data from the passed decoder
     */
    @objc required init(coder aDecoder: NSCoder)
    {
        count = aDecoder.decodeObject(forKey: "count") as? Int
        data = aDecoder.decodeObject(forKey: "data") as? CMSData
        date = aDecoder.decodeObject(forKey: "date") as? String
        message = aDecoder.decodeObject(forKey: "message") as? String
        position = aDecoder.decodeObject(forKey: "position") as? Int
        size = aDecoder.decodeObject(forKey: "size") as? Int
        spent = aDecoder.decodeObject(forKey: "spent") as? Int
        type = aDecoder.decodeObject(forKey: "type") as? String
        userMessage = aDecoder.decodeObject(forKey: "userMessage") as? String
        
    }
    
    /**
     * NSCoding required method.
     * Encodes mode properties into the decoder
     */
    @objc func encode(with aCoder: NSCoder)
    {
        if count != nil{
            aCoder.encode(count, forKey: "count")
        }
        if data != nil{
            aCoder.encode(data, forKey: "data")
        }
        if date != nil{
            aCoder.encode(date, forKey: "date")
        }
        if message != nil{
            aCoder.encode(message, forKey: "message")
        }
        if position != nil{
            aCoder.encode(position, forKey: "position")
        }
        if size != nil{
            aCoder.encode(size, forKey: "size")
        }
        if spent != nil{
            aCoder.encode(spent, forKey: "spent")
        }
        if type != nil{
            aCoder.encode(type, forKey: "type")
        }
        if userMessage != nil{
            aCoder.encode(userMessage, forKey: "userMessage")
        }
        
    }
    
}

