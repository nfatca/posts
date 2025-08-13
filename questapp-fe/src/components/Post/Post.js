import React, {useEffect, useState} from "react";
import "./Post.scss";

function Post(props) {
    const {text, title} = props;

    return (
        <div className="postContainer">
            {title}
            {text}
        </div>
    )
}
export default Post;