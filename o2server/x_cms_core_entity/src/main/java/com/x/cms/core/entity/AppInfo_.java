/** 
 *  Generated by OpenJPA MetaModel Generator Tool.
**/

package com.x.cms.core.entity;

import com.x.base.core.entity.SliceJpaObject_;
import java.lang.Boolean;
import java.lang.String;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel
(value=com.x.cms.core.entity.AppInfo.class)
@javax.annotation.Generated
(value="org.apache.openjpa.persistence.meta.AnnotationProcessor6",date="Thu Dec 27 11:40:05 CST 2018")
public class AppInfo_ extends SliceJpaObject_  {
    public static volatile SingularAttribute<AppInfo,Boolean> allPeoplePublish;
    public static volatile SingularAttribute<AppInfo,Boolean> allPeopleView;
    public static volatile SingularAttribute<AppInfo,String> appAlias;
    public static volatile SingularAttribute<AppInfo,String> appIcon;
    public static volatile SingularAttribute<AppInfo,String> appInfoSeq;
    public static volatile SingularAttribute<AppInfo,String> appMemo;
    public static volatile SingularAttribute<AppInfo,String> appName;
    public static volatile ListAttribute<AppInfo,String> categoryList;
    public static volatile SingularAttribute<AppInfo,String> creatorIdentity;
    public static volatile SingularAttribute<AppInfo,String> creatorPerson;
    public static volatile SingularAttribute<AppInfo,String> creatorTopUnitName;
    public static volatile SingularAttribute<AppInfo,String> creatorUnitName;
    public static volatile SingularAttribute<AppInfo,String> description;
    public static volatile SingularAttribute<AppInfo,String> documentType;
    public static volatile SingularAttribute<AppInfo,String> iconColor;
    public static volatile SingularAttribute<AppInfo,String> id;
    public static volatile ListAttribute<AppInfo,String> manageableGroupList;
    public static volatile ListAttribute<AppInfo,String> manageablePersonList;
    public static volatile ListAttribute<AppInfo,String> manageableUnitList;
    public static volatile ListAttribute<AppInfo,String> publishableGroupList;
    public static volatile ListAttribute<AppInfo,String> publishablePersonList;
    public static volatile ListAttribute<AppInfo,String> publishableUnitList;
    public static volatile SingularAttribute<AppInfo,Boolean> reviewed;
    public static volatile ListAttribute<AppInfo,String> viewableGroupList;
    public static volatile ListAttribute<AppInfo,String> viewablePersonList;
    public static volatile ListAttribute<AppInfo,String> viewableUnitList;
}
